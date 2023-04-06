(ns mypass.app
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [[nil "--list"]
   ])
(defn -main [& args]
  (let [parse-options (parse-opts args cli-options) 
       options (:options parse-options)]
  (cond 
    (:list options) (println "list stored passwords")
    :else (println "default"))))
