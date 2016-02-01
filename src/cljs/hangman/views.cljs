(ns hangman.views
    (:require [re-frame.core :as re-frame]))

(defn main-panel []
  (let [display-word (re-frame/subscribe [:display-word])]
    (fn []
      [:div
       {:tab-index 0
        :on-key-press (fn [e]
                        (re-frame/dispatch
                         [:try-letter
                          (char (.-which e))]))}
       "hangmag: " @display-word
       ])))
