(ns callcongressnow.views
  (:use [hiccup core page]))

(defn page [nstr]
  (html5
   [:head
    [:title "Call Congress Now"]
    (include-css "/css/bootstrap.min.css")
    (include-css "/css/index.css")
    (include-js "//static.twilio.com/libs/twiliojs/1.1/twilio.min.js")
    (include-js "/js/react.js")]
   [:body
    [:div {:id (str nstr "container") :class "container"}]
    (include-js "/js/app.js")]))

