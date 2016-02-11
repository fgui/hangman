(ns hangman.views
  (:require [re-frame.core :as re-frame]
            [hangman.game :as game]))

(defn on-key-press [event]
  (re-frame/dispatch [:try-letter (char (.-which event))])
  )

(defn score-component [word]
  (list [:div "total score: "  (game/accumulated-score {:word word})]
        [:div "score: "  (game/score word)])
  )

(defn main-panel []
  (let [word (re-frame/subscribe [:word])]
    (fn []
      [:div
       {:tab-index 0
        :on-key-press on-key-press}
       (score-component @word)
       [:table
        [:tbody
         [:td
          [:img {:src (str "img/Hangman-" (game/num-errors @word) ".png")
                 :width "100"
                 :style {:border "1px solid black"} }]]
         [:td
          [:table
           [:tbody
            [:tr [:td "Word:"] [:td (game/display-word @word)]]
            [:tr [:td "Misses:"] [:td (game/display-misses @word)]]
            (when (game/game-over? @word)
              (list [:tr [:td "Status:"] [:td (game/display-result @word)]]
                    [:tr [:td {:colspan 2}
                          [:button
                           {:on-click #(re-frame/dispatch [:new-game])}
                           "next"]]])
              )]]]]]])))
