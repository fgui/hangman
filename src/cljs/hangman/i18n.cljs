(ns hangman.i18n)

(def dictionary
  {:en {:word "Word:"
        :misses "Misses:"
        :status "Status:"
        :score "Score:"
        :total-score "Total score:"
        :won "Won"
        :lost "Lost"
        :play-again "Play again"}
   :es {:word "Palabra:"
        :misses "Fallos:"
        :status "Estado:"
        :score "Puntuación:"
        :total-score "Puntuación total:"
        :won "Ganaste"
        :lost "Perdiste"
        :play-again "Volver a jugar"}
   }
  )

(defn translate [language k]
  (get-in dictionary [language k] "missing translation")
  )
