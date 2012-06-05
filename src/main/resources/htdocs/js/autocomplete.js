(function(Autocomplete) {
  
  Autocomplete.Model = Backbone.Model.extend({
    initialize: function(options) {
      var $node = options.node
        , list = fly.mem.get($node.data('bind'))
        , required = $node.data('required')
        ;
      
      this.set('required', required);
      this.set('list', list);
    }
  });
  
  Autocomplete.View = Backbone.View.extend({
      events: {
        'focusout': 'required'
      }
  
    , required: function() {
        if (!this.model.get('required')) { return; }
        
        if (this.model.get('list').indexOf(this.$el.val()) == -1) {
          this.$el.val(this.previous);
        } else {
          this.previous = this.$el.val();
        }
      }
    
    , render: function() {
        var list = this.model.get('list');
      
        this.$el.typeahead({
          source: list
        });
        
        if (this.model.get('required')) {
          if (list.length > 0) {
            this.$el.val(list[0]);
          }
          
          this.previous = this.$el.val();
        }
        
        return this;
      }
    
    , initialize: function() {
        _.bindAll(this);
        this.$el = this.model.get('node');
      }
  });
  
})(fly.module('autocomplete'));