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
				// TODO: get rid of hardcoded value (everywhere) 
				var ref = theme._links.threads.href.slice(21); 
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
			threads: []
		};
	},

	componentDidMount : function() {
		var path = window.location.pathname;
		$.ajax({url: path}).done(
			response => {
				this.setState({ threads: response._embedded.threads });
			}
		);
	},

	render : function(){
		var list = this.state.threads.map(
			thread => {
				var ref = thread._links.messages.href.slice(21);
				return (		
					<div key={thread._links.self.href} className="thread">			
						<Link to={ref}> {thread.name} </Link>
						<p>{thread.date}</p>
					</div>
				);
			}
		);

		return (
			<div className="threads">
				{list}
			</div>
		);
	}

});

var MessageList = React.createClass({

	getInitialState: function() {
		return {
			messages: []
		};
	},

	componentDidMount : function() {
		var path = window.location.pathname;
		$.ajax({url: path}).done(
			response => {
				this.setState({ messages: response._embedded.messages });
			}
		);
	},

	render : function(){
		var list = this.state.messages.map(
			message => {
				return (		
					<div key={message._links.self.href} className="message">			
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
			</div>
		);
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
