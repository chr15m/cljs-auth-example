(ns cljs-auth-example.core
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]
    [promesa.core :as p]))

(def auth-test-endpoint
  ; "auth-failure.json"
  "auth-success.json")

(defonce state (r/atom {}))

(defn view:app [state]
  (let [username (get-in @state [:auth :username])]
    [:div
     [:h1 "cljs-auth-example"]
     [:p "Welcome to the app, " username "!"]
     [:button {:on-click #(swap! state update-in [:clicks] inc)}
      "click me"] " (clicked " (:clicks @state) " times)"]))

(defn view:login-screen [state]
  [:div
   [:h1 "please log in"]
   [:form {:on-submit
           (fn [ev]
             (.preventDefault ev)
             (js/alert "now post to auth endpoint
                       and update :auth with the result"))}
    [:input {:placeholder "username"
             :on-change #(swap! state assoc :username
                                (-> % .-target .-value))
             :value (:username @state)}]
    [:input {:placeholder "password"
             :type "password"
             :on-change #(swap! state assoc :password
                                (-> % .-target .-value))
             :value (:password @state)}]
    [:button "submit"]]])

(defn view:main [state]
  (let [authenticated? (get-in @state [:auth :authenticated])]
    (js/console.log @state)
    (if authenticated?
      [view:app state]
      [view:login-screen state])))

(defn start {:dev/after-load true} []
  (rdom/render [view:main state]
               (js/document.getElementById "app")))

(defn init []
  (p/let [response (js/fetch (str "/" auth-test-endpoint))
          json (.json response)
          auth (js->clj json :keywordize-keys true)]
    ; artificial delay to demonstrate
    ; the loading spinner while auth loads
    (p/delay 1000)
    (swap! state assoc :auth auth) 
    (start)))
