Rest-API's

* registerUser
	Header 		[]
	Body 		[User {email, password}]
	Response	[response(0/1), data(String)]
	Note: 	It is not necessary to return the user that has been registered
			
* loginUser
	Header		[]
	Body		[User {email, password}]
	Response	[response(0/1), data(String/Login{email, token})]
	Note:	It is not necessary to return the user, the front-end needs just the token and (but it's not strictly necessary) the email
	