(function(Form) {
  
  Form.Model = Backbone.Model.extend({
      submit: function(data) {
        var that = this;
        
        fly.api('GET', this.get('url'), data, function(results) {
          fly.mem.set(that.get('bind'), results);
        });
      }
  
    , initialize: function(options) {
        var $node = options.node;
        
        this.set('validate', $node.data('validate'));
        this.set('bind', $node.data('bind'));
        this.set('url', $node.data('url'));
      }
  });
  
  Form.View = Backbone.View.extend({
      events: {
        'submit': 'submit'
      }
  
    , submit: function(ev) {
        ev.preventDefault();
        
        // Remove old warnings
        this.$el.find('.alert').remove();
        
        var data = {};
        
        _.each(this.$el.find('input'), function(input) {
          var $input = $(input)
            , name = $input.attr('name')
            ;
           
          if (name) { data[name] = $input.val(); }
        });
        
        this.model.submit(data);
      }
  
    , render: function() {
        if (this.model.get('validate')) {
          var that = this
            , validation = []
            , name = this.$el.attr('name')
            ;
          
          if (!name) { 
            throw new Error('Form requires "name" attribute for validation');
          }
          
          _.each(this.$el.find('input'), function(input) {
            var $input = $(input)
              , rules = $input.data('rules')
              , name = $input.attr('name')
              ;
            
            if (!rules) { return; }
            
            if (!name) {
              throw new Error('Form requires "name" attribute for validation');
            }
            
            validation.push({
                name: name
              , rules: rules
            });
          });
          
          this.$el.unbind('submit');       
          
          new FormValidator(name, validation, function(errors, ev) {
            if (errors.length == 0) {
              that.submit(ev);
              return;
            }
            
            var alertHtml = Mustache.render(that.alertTmpl, {errors: errors});
            
            that.$el.append(alertHtml);
            that.$el.find('.alert').alert();
          });
        
        }
      
        return this;
      }
  
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
        this.alertTmpl = $('#form-alert-tmpl').html();
      }
  });
  
})(fly.module('form'));