(ns hangman.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [hangman.handlers]
              [hangman.subs]
              [hangman.views :as views]
              [hangman.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn on-key-press [event]
  (re-frame/dispatch [:try-letter (char (.-which event))]))

(defn listen-key-press []
  (.addEventListener js/document "keypress" on-key-press))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (listen-key-press)
  (mount-root))
