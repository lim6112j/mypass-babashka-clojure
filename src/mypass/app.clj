(ns mypass.app
  (:require [clojure.tools.cli :refer [parse-opts]]
            [mypass.db :refer [list-passwords]]
            [table.core :as t])
  (:gen-class))
;;https://github.com/cldwalker/table

(def cli-options
  ;; An option with a required argument
  [[nil "--list"]])
(defn -main [& args]
  (let [parsed-options (parse-opts args cli-options)
        options (:options parsed-options)]
    (cond
      (:list options) (t/table (list-passwords))
      :else (println "default"))))
