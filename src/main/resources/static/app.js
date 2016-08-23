import React from 'react'
import ReactDOM from 'react-dom';
import { Router, Route, Redirect, browserHistory, Link} from 'react-router'
import $ from 'jquery'

var ThemeList = React.createClass ({

	getInitialState: function() {
		return {
			themes: []
		};
	},

	componentDidMount : function() {
		var path = window.location.pathname;
		$.ajax({url: path}).done(
			response => {
				this.setState({ themes: response._embedded.themes });
			}
		);
	},

	render : function(){
		var list = this.state.themes.map(
			theme => {
				var urlParts = theme._links.threads.href.split('/');
				var ref = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5]; 
				return (		
					<div key={theme._links.self.href} className="theme">			
						<Link to={ref}> {theme.name} </Link>
					</div>
				);
			}
		);

		return (
			<div className="themes">
				{list}
			</div>
		);
	}

});

var ThreadList = React.createClass({

	getInitialState: function() {
		return {
			threads: [],
			links: {
				self  : {href: ''}
			}
		};
	},

	componentDidMount : function() {
		this.updateThreads();
	},

	updateThreads: function() {
		var urlParts = window.location.href.split('/');
		var path = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5];
		$.ajax({url: path}).done(
			response => {
				this.setState({ 
					links: response._links
				});
				this.setState({
					threads: response._embedded.threads
				});
			}
		);
	},

	addThread: function(name){
		var urlParts = this.state.links.self.href.split('/');
		var url = '/' + urlParts[3] + '/' + urlParts[4]; 
		var json = {"name": name};

	    if (!name) {
	      return;
	    }

     	$.ajax({
	      url: url,
	      contentType: 'application/json',
	      dataType: 'json',
	      type: 'POST',
	      data: JSON.stringify(json),
	      async: false
	    });

		this.updateThreads();
	},

	render : function(){
		var list = this.state.threads.map(
			thread => {
				var urlParts = thread._links.messages.href.split('/');
				var ref = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5];

				var style = thread.closed ? 'thread thread-closed' : 'thread thread-opened'

				return (		
					<div key={thread._links.self.href} className={style}>			
						<Link to={ref}> {thread.name} </Link>
						<p>{thread.dateUpdated}</p>
					</div>
				);
			}
		);

		return (
			<div className="threads">
				<AddThreadForm addThread={this.addThread}/>
				{list}
				<PageButtons links={this.state.links} updateThreads={this.updateThreads}/>
			</div>
		);
	}

});

var AddThreadForm = React.createClass({
	getInitialState: function() {
		return {
			folded: true,
			name: ''
		};
	},

	handleNameChange: function(e){
		this.setState({name: e.target.value});
	},

	handleSubmit: function(e) {
	    e.preventDefault();
	    var name = this.state.name.trim();
	    
	    this.props.addThread(name);

     	this.toggleFolded();
	    this.setState({name: ''});
  	},

	toggleFolded: function(){
		this.setState({
      		folded: !this.state.folded
    	});
	},

	render: function(){
		if( this.state.folded )
			return (
				<div className="thread-form" onClick={this.toggleFolded}> + </div>
			);
		else
			return(
				<div className="thread-form">
					<form onSubmit={this.handleSubmit}>
				        <input 
				         type="text" 
				         placeholder="Thread name" 
				         onChange={this.handleNameChange} 
				         value={this.state.name}
				        />
				        <input type="submit" value="Add" />
			      	</form>
				</div>
			);
	}
});

var MessageList = React.createClass({

	getInitialState: function() {
		return {
			messages: [],
			links: {
				self  : {href: ''}
			}
		};
	},

	componentDidMount : function() {
		this.updateMessages();
	},

	updateMessages : function() {
		var path = window.location.pathname;
		$.ajax({url: path}).done(
			response => {
				this.setState({ 
					links: response._links
				});
				this.setState({ 
					messages: response._embedded.messages 
				});
			}
		);
	},

	addMessage: function(content, image){
		var urlParts = this.state.links.self.href.split('/');
		var url = '/' + urlParts[3] + '/' + urlParts[4];
		var json = {"content": content, "image": image};


	    if (!content || !image) {
	      return;
	    }

     	$.ajax({
	      url: url,
	      contentType: 'application/json',
	      dataType: 'json',
	      type: 'POST',
	      data: JSON.stringify(json),
	      async: false
	    });

		this.updateMessages();
	},

	render : function(){
		var list = this.state.messages.map(
			message => {
				return (		
					<div key={message._links.self.href} className="message">
						<p>{message.messageId}</p>			
						<p>{message.image}</p>
						<p>{message.content}</p>
						<p>{message.date}</p>
					</div>
				);
			}
		);

		return (
			<div className="messages">
				{list}
				<PageButtons links={this.state.links} updateMessages={this.updateMessages}/>
				<AddMessageForm addMessage={this.addMessage}/>
			</div>
		);
	}

});

var AddMessageForm = React.createClass({
	getInitialState: function() {
		return {
			image: '',
			content: ''
		};
	},

	handleImageChange: function(e){
		this.setState({
			image: e.target.value
		});
	},

	handleContentChange: function(e){
		this.setState({
			content: e.target.value
		});
	},

	handleSubmit: function(e) {
	    e.preventDefault();
	    var image = this.state.image.trim();
	    var content = this.state.content.trim();
	    
	    this.props.addMessage(content, image);

	    this.setState({
	    	image: '',
			content: ''	
		});
  	},


	render: function(){
		return(
			<div className="message-form">
				<form onSubmit={this.handleSubmit}>
			        <input 
			         type="text" 
			         placeholder="Image URL" 
			         onChange={this.handleImageChange} 
			         value={this.state.image}
			         className="msg-form-img"
			        />
			        <input 
			         type="text" 
			         placeholder="Type your message here..." 
			         onChange={this.handleContentChange} 
			         value={this.state.content}
			         className="msg-form-content"
			        />
			        <input type="submit" value="Post" className="msg-form-btn"/>
		      	</form>
			</div>
		);
	}
});

var PageButtons = React.createClass({

	contextTypes: {
    	router: React.PropTypes.object.isRequired
  	},

	handleNavFirst: function(){
		var urlParts = this.props.links.first.href.split('/');
		var ref = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5];
		
		this.context.router.push(ref);
		this.props.updateThreads();
	},

	handleNavPrev: function(){
		var urlParts = this.props.links.prev.href.split('/');
		var ref = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5];

		this.context.router.push(ref);
		this.props.updateThreads();
	},

	handleNavNext: function(){
		var urlParts = this.props.links.next.href.split('/');
		var ref = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5];

		this.context.router.push(ref);
		this.props.updateThreads();
	},

	handleNavLast: function(){
		var urlParts = this.props.links.last.href.split('/');
		var ref = '/' + urlParts[3] + '/' + urlParts[4] + '/' + urlParts[5];

		this.context.router.push(ref);
		this.props.updateThreads();
	},

	render: function(){
		return(
			<div className="page-buttons">
				<button onClick={this.handleNavFirst} disabled={!this.props.links.first} className="page-button" key="first"> FIRST </button>
				<button onClick={this.handleNavPrev} disabled={!this.props.links.prev} className="page-button" key="prev"> PREV </button>
				<button onClick={this.handleNavNext} disabled={!this.props.links.next} className="page-button" key="next"> NEXT </button>
				<button onClick={this.handleNavLast} disabled={!this.props.links.last} className="page-button" key="last"> LAST </button>
			</div>
		)
	}

});

ReactDOM.render((
  <Router history={browserHistory}>
  	<Redirect from="/" to="/themes"/>
    <Route path="/themes" component={ThemeList}/>
    <Route path="/themes/:themeId/threads" component={ThreadList}/>
	<Route path="/threads/:threadId/messages" component={MessageList}/>
  </Router>
), document.getElementById('react'));
