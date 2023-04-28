(ns mypass.password)
(defn generate-password [length]
  (let [available-chars (reduce (fn [acc val]
                                  (str acc (char val))) "" (range 33 128))]
    (loop [password ""]
      (if (= (count password) length)
        password
        (recur (str password (rand-nth available-chars)))))))
