(function(Init) {
  
  Init.Model = Backbone.Model.extend({
    initialize: function(options) {
      var url = options.node.data('url');
      
      if (!url) { return; }
      
      fly.api('GET', url, {}, function(results) {
        for (i in results) {
          if (results.hasOwnProperty(i)) {
            fly.mem.set(i, results[i]);
          }
        }
        
        fly.mem.trigger('init');
      });
    }
  });
  
  Init.View = Backbone.View.extend({
      render: function() {
        this.$el.css('display', 'none');
        return this;
      }
  
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
      }
  });
  
})(fly.module('init'));