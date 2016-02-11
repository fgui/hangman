(ns hangman.views
  (:require [re-frame.core :as re-frame]
            [hangman.game :as game]))

(defn on-key-press [event]
  (re-frame/dispatch [:try-letter (char (.-which event))])
  )

(defn score-component []
  (let [state (re-frame/subscribe [:state])
        round (re-frame/subscribe [:round])]
    (fn []
      [:div
       [:div "total score: "
             (game/accumulated-score @state)]
       [:div "score: "  (game/score @round)]]))
  )

(defn main-panel []
  (let [round (re-frame/subscribe [:round])]
    (fn []
      [:div
       {:tab-index 0
        :on-key-press on-key-press}
       [score-component]
       [:table
        [:tbody
         [:td
          [:img {:src (str "img/Hangman-" (game/num-errors @round) ".png")
                 :width "100"
                 :style {:border "1px solid black"} }]]
         [:td
          [:table
           [:tbody
            [:tr [:td "Word:"] [:td (game/display-word @round)]]
            [:tr [:td "Misses:"] [:td (game/display-misses @round)]]
            (when (game/game-over? @round)
              (list [:tr [:td "Status:"] [:td (game/display-result @round)]]
                    [:tr [:td {:colspan 2}
                          [:button
                           {:on-click #(re-frame/dispatch [:new-round])}
                           "play again"]]])
              )]]]]]])))
