# Fly Framework

Quickly build database centric applications in Java with quick and modern web UIs without the need to use large bloated frameworks or write a line of Javascript. No need to worry about complicated deployment processes or maintenance, Fly applications are distributed as a single JAR which includes an embedded web server.

## Basic concepts

The Fly framework was conceived with a single goal in mind, to quickly and efficiently build database centric web applications. These sorts of applications are mainly used in enterprise to give non-programmers easy access to update, modify and create production data without giving them free access to the database.

Fly contains both a server and client component. Each may be used independently but the true power of the framework is made clear when using both together.

### Server

The server components allows you to build a RESTfull JSON API. Unlike some more popular Java framework every server call is stateless and simply returns a JSON object. All application state is kept in the database, it is the single point of truth. Building stateless routes and API calls makes for very scalable and testable code. All routes are name spaced and contained within a Servlet which allows you to build a clean modular API to which you can add and remove Servlets as the specifications change over time.

### Client

The client side is built purely in HTML and is a UI built with simple reusable components. This is a modern style web UI which uses lots of Javascript to have a snappy and interactive feel, but never requires a line of JS from the Fly developer. The only thing the developer needs to do is write basic HTML with custom data attributes to let the framework know what data should be bound to which components.  

## Starting the Server

The main method for your Fly application is extremely simple. Tell the framework which folder in your Classpath contains your HTML, which port the server should run on and how the Servlets should be mounted and that is all. For example here is the Main.java class from the [fly-test](https://github.com/SoapyIllusions/fly-test) application.

    package com.angelini.flyTest;

    import com.angelini.fly.Fly;
    import com.angelini.fly.FlyDB;

    public class Main {
	
    	public static final int PORT = 8090;
	
    	private static String CONNECTION = "jdbc:mysql://localhost/Fly";
    	private static final String USER = "alex";
    	private static final String PASS = "alex";
	
    	public static void main(String[] args) {
    		try {
    			FlyDB db = new FlyDB(CONNECTION, USER, PASS);
    			Fly server = new Fly(PORT, db, "/htdocs");
			
    			server.addServlet(ProductRoutes.class, "/products/*");
			
    			server.start();
			
    		} catch (Exception e) {
    			System.out.println("Server startup error" + e.message);
    		}
    	}

    }

## Routing

Routes are very simple using Fly, every routed method from your Servlet receives an HttpRequest and HttpResponse object which allow them to get information about the current request and allows the developer to push information back out to the user. All these methods are controlled by a Router annotation which tells the framework where the API endpoint is for that method.

Remember the routes are relative to where the Servlet has been mounted. For example if the ProductRoutes classes is mounted in Main.java at "/products/*" then a route to "/cart" translates as "/products/cart" as the API call's full path.

Here is an example of a route class from [fly-test](https://github.com/SoapyIllusions/fly-test). This route fetches all the products from the MySQL database and returns them as a JSON object using HttpResponse.sendObject(). It fetches query string parameters using HttpRequest.getQuery(). And it pulls a database connection off the pool then closes it before ending the request. Notice how this request holds no state, it is injected with all it needs to function and for the same inputs always does the same operation.

    package com.angelini.flyTest;

    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.SQLException;
    import java.util.Date;
    import java.util.List;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import com.angelini.fly.FlyDB;
    import com.angelini.fly.HttpRequest;
    import com.angelini.fly.HttpResponse;
    import com.angelini.fly.RoutedServlet;
    import com.angelini.fly.Router;

    public class ProductRoutes implements RoutedServlet {

    	private FlyDB db;
    	private static Logger log = LoggerFactory.getLogger(ProductRoutes.class);
	
    	public void init(FlyDB db) {
    		this.db = db;
    	}
	
    	@Router(route = "/", verb = Router.GET)
    	public void allProducts(HttpRequest req, HttpResponse resp) throws IOException {
    		String name = req.getQuery("name");
    		String type = req.getQuery("type");
    		Date date = req.getQueryDate("date");
		
    		try {
    			Connection conn = db.getConn();
    			List<Product> products = ProductService.getProducts(conn, name, date, type);
    			conn.close();
			
    			resp.sendObject(products);
		
    		} catch (SQLException e) {
    			resp.setStatus(500);
    			log.error("Products fetch exception", e);
    		}
    	}
	
    }

## Components

The client side is built entirely in HTML using custom data attributes. The HTML files have no `<html>`, `<head>` or `<body>` tags as they are injected in by the framework, the only tags needed are the tags which described the components used on the page. The following example builds the search section in [fly-test](https://github.com/SoapyIllusions/fly-test) and the form calls the route in the example just above.

    <form name="search" data-ftype="form" data-url="/products" data-bind="searchResults" data-validate="true" class="span2">
      <h3 style="margin-bottom: 15px;">Search</h3>
      <div>
        <label>Name:</label><input type="text" data-ftype="input" name="name" data-rules="alpha_numeric" />
      </div>
      <div>
        <label>Release Date:</label><input type="text" data-ftype="date" name="date"/>
      </div>
      <div>
        <label>Type:</label><div data-ftype="dropdown" data-bind="types" data-name="type"></div>
      </div>
      <input class="btn" type="submit" value="Submit" style="margin-top: 10px;" />
    </form>

    <div class="span10">
      <h3 style="margin-bottom: 15px;">Products</h3>
      <div data-ftype="table" data-bind="searchResults" data-selected="rowSelect">
        <div data-ftype="table:column" data-header="ID" data-bind="id" data-index="true"></div>
        <div data-ftype="table:column" data-header="Name" data-bind="name"></div>
        <div data-ftype="table:column" data-header="Price" data-bind="price"></div>
        <div data-ftype="table:column" data-header="Description" data-bind="description"></div>
        <div data-ftype="table:column" data-header="Release Date" data-bind="releaseDate"></div>
        <div data-ftype="table:column" data-header="Type" data-bind="type"></div>
      </div>
    </div>
    
In this example we can see many different components being used. Let's walk through some of them:

#### Table

This is a table of data which is bound to a List of Objects. we can see that it will be rendered as a table components thanks to the `data-ftype="table"` attribute. We can see it is bound to the searchResults variable thanks to `data-bind="searchResults"` and that whenever a row is selected in the table it is bound to the rowSelect variable. The table's columns are described in the table's children divs as well as which object parameter they are bound to.

#### Form

The form component, like a normal form sends the data of within children inputs to the server. Except our form is describes using data attributes and the request is done by Ajax and not with a page refresh. All the input data will be sent as query parameters and the request will be done using a GET request (other verbs coming soon). `data-validate="true"` tells Fly that there are `data-rules` on some inputs to be checked before making the Ajax call.


### Validation

Fly does client side validation using validate.js, the syntax can be seen in fly-test but the exact validate.js rules can be found at [Validate.js](http://rickharrison.github.com/validate.js/)

### Grid System

Fly uses twitter bootstrap as a CSS framework and it includes a grid system, which is used in this demo. For more information please check out [Twitter Bootstrap Scaffolding](http://twitter.github.com/bootstrap/scaffolding.html).

## Thanks To

Obviously as an open source project, this is built on the shoulder's of giants and I need to thank many developers who's libraries are used by this project.

* [Jetty](http://jetty.codehaus.org/jetty/)
* [jQuery](http://jquery.com/)
* [Twitter Bootstrap](http://twitter.github.com/bootstrap/index.html)
* [Backbone.js](http://documentcloud.github.com/backbone/)
* [SLF4J](http://www.slf4j.org/)
* [Mustache.js](https://github.com/janl/mustache.js/)
* [Bootstrap Datepicker](https://github.com/eternicode/bootstrap-datepicker)
* [Validate.js](http://rickharrison.github.com/validate.js/)
* [GSON](http://code.google.com/p/google-gson/)
* [Bone CP](http://jolbox.com/)
* [DataTables](http://datatables.net/)

## TODO

* More complete documentation of all available features
* Login system
* Client side namespacing
* New components