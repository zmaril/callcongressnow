# Call Congress Now

A [Heroku](http://www.heroku.com) web app using Compojure that lets
you bleed the internet over into the most glorious american congress.

This was built using Om. Many words are forthcoming on that
experience. Needless to say, Om is terrible.

## Usage

After you've acquired twilio credentials, run the app locally with:

```sh
APP_SID=YOURS TWILIO_SID=YOURS TWILIO_AUTH=YOURS lein run -m callcongressnow.web
```

To run on heroku, configure your twilio app's voice url to point to
`your.herokuapp.com/voice`. You also need to set $PHONE_NUMBER to a valid phone number
e.g. `heroku config:set PHONE_NUMBER=1-123-4567`.

Copyright © 2014 Zack Maril

Distributed under the Eclipse Public License, the same as Clojure.
