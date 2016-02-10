(ns hangman.handlers
    (:require [re-frame.core :as re-frame]
              [hangman.db :as db]
              [hangman.game :as game]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/state))

(re-frame/register-handler
 :try-letter
 (fn  [db [_ letter]]
   (if-not (game/game-over? (:word db))
     (update-in db [:word :tried-letters] conj letter)
     db)
   ))

(re-frame/register-handler
 :init-word
 (fn [db [_ word]]
   (assoc db :word (game/guess-this-word word))
   ))
