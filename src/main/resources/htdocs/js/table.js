(function(Table) {
  
  var dataTableLayout = "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>";
  
  Table.Model = Backbone.Model.extend({
    initialize: function(options) {
      var $node = $(options.node)
        , columns = []
        ;
      
      _.each($node.find('[data-child="column"]'), function(child, i) {
        var $child = $(child);
        columns.push({ sTitle: $child.data('header')
                     , bind: $child.data('bind')
                     , index: $child.data('index')
                     , node: $child });
      });
      
      this.set('columns', columns);
      this.set('bind', $node.data('bind'));
      this.set('selected', $node.data('selected'));
    }
  });
  
  Table.View = Backbone.View.extend({
      events: {
        'click tr': 'selectRow'
      }
    
    , selectRow: function(ev) {
        var $row = $(ev.currentTarget)
          , $table = $('#' + this.model.get('table-id'))
          ;
        
        if ($row.hasClass('row_selected')) {
          $row.removeClass('row_selected');
        } else {
          $table.dataTable().$('tr.row_selected').removeClass('row_selected');
          $row.addClass('row_selected');
          
          var index = null;
          
          _.each(this.model.get('columns'), function(col, i) {
            if (col.index) {
              index = $($row.children()[i]).text();
            }
          });
          
          fly.mem.set(this.model.get('selected'), index);
        }
      }
  
    , render: function() {
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