Minimal ClojureScript frontend auth example.

`make watch` to run.

* Change `auth-test-endpoint` to `auth-failure.json` on line 7 to demonstrate failing auth that then shows the login view. You could also redirect to your app's login page (and have it redirect back to the frontend after logging in) in the `init` function.
* Note on line 57 there is an artificial delay to demonstrate how the loading spinner waits until the auth data has been fetched.
* In a real app you would create a `/api/auth` endpoint which returns the JSON (or transit) formatted auth data for the current user session instead of requesting json files. The json files are just for demonstration purposes.

This repo was created with the following command and then modified:

* `npm init shadowfront cljs-auth-example`
