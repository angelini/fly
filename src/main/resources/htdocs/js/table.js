(function(Table) {
  
  var dataTableLayout = "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>";
  
  Table.Model = Backbone.Model.extend({
    initialize: function(options) {
      var $node = $(options.node)
        , columns = []
        ;
      
      _.each($node.find('[data-ftype="table:column"]'), function(child, i) {
        var $child = $(child);
        columns.push({ sTitle: $child.data('header')
                     , bind: $child.data('bind')
                     , node: $child });
      });
      
      this.set('columns', columns);
      this.set('bind', $node.data('bind'));
    }
  });
  
  Table.View = Backbone.View.extend({
      render: function() {
        var id = 'fly-search-' + fly.idGen()
          , tableHtml = Mustache.render(this.template, {id: id})
          , columns = this.model.get('columns')
          , data = []
          ;
        
        this.$el.html(tableHtml);
        
        _.each(fly.mem.get(this.model.get('bind')), function(bound) {
          var row = [];
          
          for (var i = 0; i < columns.length; i++) {
            row.push(bound[columns[i].bind]);
          }
          
          data.push(row);
        });
        
        $('#' + id).dataTable({
            aaData: data
          , aoColumns: columns
          , sDom: dataTableLayout
          , sPaginationType: "bootstrap"
        });
        
        this.model.set('table-id', id);
        
        return this;
      }

    , initialize: function() {
        _.bindAll(this);
        this.template = $('#table-tmpl').html();
        this.$el = this.model.get('node');
        
        fly.mem.on('change:' + this.model.get('bind'), this.render);
      }
  });
  
})(fly.module('table'));