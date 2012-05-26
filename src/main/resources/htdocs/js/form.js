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
        return this;
      }
  
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
      }
  });
  
})(fly.module('form'));