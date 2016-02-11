(ns hangman.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :round
 (fn [db]
   (reaction (:round @db))))

(re-frame/register-sub
 :state
 (fn [db]
   (reaction @db)))
