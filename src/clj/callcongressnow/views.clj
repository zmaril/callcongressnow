(ns callcongressnow.views
  (:use [hiccup core page]))

(defn page [nstr]
  (html5
   [:head
    [:title "Call Congress Now"]
    (include-css "/css/bootstrap.min.css")
    (include-css "/css/index.css")
    (include-js "//static.twilio.com/libs/twiliojs/1.1/twilio.min.js")]
   [:body
    [:div {:id "container" :class "container"}]
    (include-js "/js/react.js")
    (include-js "/js/out/goog/base.js")
    (include-js "/js/app.js")
    [:script {:type "text/javascript"}
     (str "goog.require(\"callcongressnow." nstr "\");" )]]))

