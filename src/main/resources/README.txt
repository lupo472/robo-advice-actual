Rest-API's

* registerUser
	Header 		[]
	Body 		[User {email, password}]
	Response	[response(0/1), data(String)]
	Note: 	It is not necessary to return the user that has been registered
			