(ns hangman.handlers
    (:require [re-frame.core :as re-frame]
              [hangman.db :as db]
              [hangman.game :as game]
              [hangman.dictionary :as dictionary]
              ))


(defn init-word [db word]
  (assoc db :word (game/guess-this-word word))
  )

(defn new-random-word [db]
  (init-word db (dictionary/random-word))
  )

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (new-random-word db/state)))

(re-frame/register-handler
 :try-letter
 (fn  [db [_ letter]]
   (if-not (game/game-over? (:word db))
     (update-in db [:word :tried-letters] conj letter)
     db)
   ))

(re-frame/register-handler
 :new-game
 (fn [db [_ _]]
   (new-random-word db)
   ))
