fly = {
    module: (function() {
      var modules = {};

      return function(name) {
        if (modules[name]) { return modules[name]; }
        modules[name] = {};
        return modules[name];
      };
    }())

  , idGen: (function() {
      var i = 0;
      return function() { return i++; }
    })()
    
  , api: function(method, url, data, cb) {
      url = _.map(url.split("/"), function(string) { 
        if (string.length > 0 && string.charAt(0) == ':') {
          string = data[string.substring(1)];
        } 
        
        return string; 
      }).join("/");
    
      var req = $.ajax({
          url: url
        , type: method
        , data: data
        , contentType: 'application/json'
      });
      
      req.done(cb);
      req.fail(function(err) {
        console.log('API Request Failed', arguments);
      });
    }

  , walk: function(node, fn) {
      fn(node);
      node = node.firstChild;
      while (node) {
        fly.walk(node, fn);
        node = node.nextSibling;
      }
    }

  , parse: function(root) {
      fly.walk(root, function(node) {
        var $node = $(node)
          , data = $node.data()
          ;
        
        if (!data) { return; }
        if (data.ftype && data.ftype.indexOf(':') == -1) {
          var Module = fly.module(data.ftype);
          
          if (!_.isEmpty(Module)) {
            var model = new Module.Model({node: $node})
              , view = new Module.View({model: model, node: $node})
              ;
            
            view.render();
          }
        }
      });
    }
  
  , setComponents: function() {
      var $customs = $('[data-ftype="custom"]');
      
      _.each($customs, function(custom) {
        var $custom = $(custom)
          , name = $custom.data('name')
          ;
        
        if (!name) {
          throw new Error('Custom tags require "data-name" attribute');
        }
        
        $custom.replaceWith($('#' + name).html());
      });
    }
  
  , setQueryString: function() {
      var that = this
        , split = window.location.href.split('?')
        ;
      
      if (split.length == 1) { return; }
      
      var params = split[1].split('&');
      _.each(params, function(param) {
        var kVal = param.split('=');
        if (kVal.length != 2) { return; }
        
        that.mem.set(kVal[0], kVal[1]);
      });
    }
  
  , mem: new Backbone.Model({})
}

// Start

jQuery(function($) {
  fly.mem.on('init', function() {
    _.each($('.fly-root'), fly.parse);
  });
  
  fly.setComponents();
  fly.setQueryString();
  fly.parse($('[data-ftype="init"]')[0]);
});