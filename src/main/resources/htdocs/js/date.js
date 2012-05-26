(function(Date) {
  
  Date.Model = Backbone.Model.extend({});
  
  Date.View = Backbone.View.extend({
      render: function() {
        this.$el.datepicker();
        return this;
      }
  
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
      }
  });
  
})(fly.module('date'));