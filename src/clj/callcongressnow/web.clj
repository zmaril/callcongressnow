(ns callcongressnow.web
  (:require [callcongressnow.views :refer [page]]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.util.response :as resp]
            [ring.middleware.stacktrace :as trace]
            [ring.middleware.session :as session]
            [ring.middleware.session.cookie :as cookie]
            [ring.adapter.jetty :as jetty]
            [cheshire.core :as json]
            [clj-http.client :as client]
            [environ.core :refer [env]])
  (:import [com.twilio.sdk
            TwilioRestClient
            TwilioRestException]
           [com.twilio.sdk.resource.instance
            Account Call]
           [com.twilio.sdk.resource.factory
            CallFactory]
           [com.twilio.sdk.client
            TwilioCapability
            TwilioCapability$DomainException]
           [com.twilio.sdk.verbs
            Dial Client 
            TwiMLResponse
            TwiMLException]))

(def capability (TwilioCapability. (env :twilio-sid) (env :twilio-auth)))
(.allowClientOutgoing capability (env :app-sid))
(def client (TwilioRestClient. (env :twilio-sid) (env :twilio-auth)))
(def main-account (.getAccount client))
(def call-factory (.getCallFactory main-account))
(def apikey "0c5186d6a83149a5abdc4b29f76ae080")
(def legislators-url
  "https://congress.api.sunlightfoundation.com/legislators")

(defroutes app
  (GET "/about"   [] (page "about"))
  (GET "/contact" [] (page "contact"))
  (GET "/profile" [] (resp/redirect "/"))
  (GET "/profile/:id" [] (page "profile"))
  (GET "/" [] (page "index"))
  (GET "/token" {params :query-params} 
       (-> {:token (.generateToken capability)}
           json/generate-string
           resp/response
           (resp/content-type "text/javascript")
           (update-in [:body]
                      #(str (get params "callback") "(" % ")"))))
  (POST "/voice" [bioguide_id]
        (println bioguide_id)
        (let [pms  {"apikey"    apikey
                    "bioguide_id"  bioguide_id}
              phone (-> (client/get legislators-url 
                                    {:as :json
                                     :query-params pms})
                        :body :results first :phone)
              phone  (if (= "me" bioguide_id) (env :my-number) phone)
              phone (apply str (filter #(not= % \-) phone))
 
              twiml (TwiMLResponse.)
              dial (Dial.)] 
          (.append dial (com.twilio.sdk.verbs.Number. phone))
          (.append dial (Client. "jenny"))
          (.setCallerId dial (env :phone-number))
          (.append twiml dial)         
          (-> (.toXML twiml)
              resp/response 
              (resp/content-type "application/xml"))))
  (route/resources "/"))

(defn wrap-error-page [handler]
  (fn [req]
    (try (handler req)
         (catch Exception e
           {:status 500
            :headers {"Content-Type" "text/html"}
            :body (slurp (io/resource "500.html"))}))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))
        ;; TODO: heroku config:add SESSION_SECRET=$RANDOM_16_CHARS
        store (cookie/cookie-store {:key (env :session-secret)})]
    (jetty/run-jetty (-> #'app
                         ((if (env :production)
                            wrap-error-page
                            trace/wrap-stacktrace))
                         (site {:session {:store store}}))
                     {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
