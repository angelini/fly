(function(Link) {
  
  Link.Model = Backbone.Model.extend({
      click: function() {
        var url = this.get('link') + '?';
        
        _.each(this.get('params'), function(param) {
          url += param + '=' + fly.mem.get(param) + '&';
        });
        
        url = url.slice(0, -1);
        
        window.location.href = url;
      }
    
    , initialize: function(options) {
        var $node = $(options.node)
          , paramStr = $node.data('param')
          , params = []
          ;
        
        _.each(paramStr.split(','), function(param) {
          params.push($.trim(param));
        });
        
        this.set('link', $node.data('link'));
        this.set('params', params);
      }
  });
  
  Link.View = Backbone.View.extend({
      events: {
        'click': 'click'
      }

    , click: function() {
        this.model.click();
      }
    
    , render: function() {
        return this;
      }
      
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
      }
  });
  
})(fly.module('link'));
