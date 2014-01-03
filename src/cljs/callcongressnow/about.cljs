(ns callcongressnow.about
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]            
            [callcongressnow.app :refer 
             [navbar footer]]
            [callcongressnow.state :refer 
             [app-state]]))
(enable-console-print!)
(defn about [_ _]
  (om/component
   (dom/div 
    #js {:id "content"
         :className "col-lg-6"}
    (dom/h2 nil 
            "About")
    (dom/p nil
           "\"Call Congress Now\" is meant to provide citizens a
           conduit to the legislators who would influence public
           policy without filtering or otherwise distorting their
           points of view.")
    (dom/h2 nil 
            "Motivation")
    (dom/p nil
           "The breadth of the recently revealed NSA surveillance
           programs and the audacity of a group of legislators to
           shutdown the government infuriated and saddened me. For
           whatever reason, both these events hit me hard and I let
           them create too much stress in my life. Someone older than
           myself told me that growing disillusioned with the
           government is part of becoming an adult. I didn't want to
           quite be an adult yet so I set out to find ways to
           influence the government.")
    (dom/p nil
           "But what could I do? I'm just a young twenty something who
           spends too much time on the computer. Influencing
           legislators seemed like an impossible task for one person,
           especially a nobody like me. The top comment of every
           article on the shutdown and the NSA business echoed the
           same advice to call senators and representatives and let
           fly your grievances. In the dilemma of a citizen in a large
           democratic nation, one phone call didn't seem like it would
           be enough to really make that much of difference. So I
           started sniffing around how to build a system to increase
           participation in government by the average citizen.")
    (dom/p nil
           )
    (dom/h2 nil
            "Generalization")
    (dom/p nil
           "I go to school at one of the most conservative and
           republican universities in the United States. While I was
           mulling over my dilemma, I was overhearing people venting
           vitriol over Obamacare everyday, almost everywhere. I'm
           admittedly more liberal than conservative on the American
           political spcetrum, but I can appreciate that there is a
           group of people who felt that same lack of agency I did but
           over completely different issues. I figured it would be
           simpler to create an unbiased tool than a biased one, so I
           focused on creating something that would let anyone quickly
           voice their opinion to Congress.")
    (dom/h2 nil 
            "Specification")
    (dom/p nil
           "\"Call Congress Now\" is my first attempt at building a
           system that I think could influence Congress and increase
           participation in government by citizens. It allows anyone,
           anywhere, to call the Washington office of any member of
           Congress at any time (I can make no guarantees about who,
           if anyone, will pick up though). It's a pretty rough sketch
           of what I have in mind, more of a proof of concept than
           anything else. Many ideas were left on the cutting room
           floor. I do hope though that \"Call Congress Now\" will be
           used and be found useful. If you have any comments,
           complaints, or criticisms, please "
           (dom/a #js {:href "/contact"} "contact me") ". For those
           interested in that sort of thing, the " 
           (dom/a
            #js {:href "https://github.com/zmaril/callcongressnow"} "source
           code is available on Github") "." )
    (dom/h2 nil 
            "Thanks")
    (dom/p nil
           "\"Call Congress Now\" is built with a variety of open
           source tools. A special thanks to Rich Hickey and the
           Clojure community for creating such a good language and
           ecosystem. An extra special thanks to "
           (dom/a 
            #js {:href "http://swannodette.github.io/2013/12/17/the-future-of-javascript-mvcs/"} 
            "David Nolen for his work on Clojurescript and Om") 
           ". Bootstrap and the \"Journal\" theme from Bootswatch was
           used as well. The source code for this website is minimal
           thanks to "
           (dom/a #js {:href "https://www.twilio.com/"} "Twilio") ", "
           (dom/a #js {:href "https://www.heroku.com/"} "Heroku") ", and the "
           (dom/a #js {:href "http://sunlightfoundation.com/"} "Sunlight Foundation's ")
           (dom/a
            #js {:href "http://sunlightlabs.github.io/congress/"} "Congress
           API") ". Finally, thanks to all my friends and family who
           have put up with me ranting about Congress the past few
           months and have helped test and use this project." ))))

(defn about-app [app]
  (reify 
    om/IRender
    (render [_ owner]
      (dom/div nil
               (om/build navbar app)
               (om/build about  app)))))

(when-let [root (.getElementById js/document "aboutcontainer")]
  (om/root app-state about-app root))

