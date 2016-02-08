(ns hangman.views
  (:require [re-frame.core :as re-frame]
            [hangman.game :as game]))

(defn on-key-press [event]
  (re-frame/dispatch [:try-letter (char (.-which event))])
  )

(defn main-panel []
  (let [word (re-frame/subscribe [:word])]
    (fn []
      [:div
       {:tab-index 0
        :on-key-press on-key-press}
       [:table
        [:tbody
         [:td
          [:img {:src (str "img/Hangman-" (game/num-errors @word) ".png")
                 :width "100"}]]
         [:td
          [:table
           [:tbody
            [:tr [:td "Word:"] [:td (game/display-word @word)]]
            [:tr [:td "Errors:"] [:td (game/display-errors @word)]]
            (when (game/game-over? @word)
              [:tr [:td "Status:"] [:td (game/display-result @word)]])]]]]]])))
