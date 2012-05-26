(function(Input) {
  
  Input.Model = Backbone.Model.extend({});
  
  Input.View = Backbone.View.extend({
      render: function() {
        var val = fly.mem.get(this.model.get('bind'));

        this.$el.val(val);
        
        return this;
      }
    
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
        
        fly.mem.on('change:' + this.model.get('bind'), this.render);
      }
  });
  
})(fly.module('input'));