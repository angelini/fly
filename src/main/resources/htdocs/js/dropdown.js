(function(Dropdown) {
  
  Dropdown.Model = Backbone.Model.extend({
    initialize: function(options) {
      var list = fly.mem.get(options.node.data('bind'));
      this.set('list', list);
    }
  });
  
  Dropdown.View = Backbone.View.extend({
      events: {
        'change select': 'change'
      }
  
    , change: function() {
        var $input = this.$el.find('input');
        $input.val(this.$el.find('select').val())
      }
    
    , render: function() {
        var options = { list: this.model.get('list')
                      , name: this.$el.data('name') }
          , html = Mustache.render(this.template, options)
          ;
        
        this.$el.html(html);
        
        var $input = this.$el.find('input');
        $input.val(this.$el.find('select').val());
        
        return this;
      }
      
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
        this.template = $('#dropdown-tmpl').html();
      }
  });
  
})(fly.module('dropdown'));