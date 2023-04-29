(ns mypass.app
  (:require [clojure.tools.cli :refer [parse-opts]]
            [mypass.db :as db]
            [table.core :as t]
            [mypass.password :refer [generate-password]]
            [mypass.stash :as stash]
            [mypass.clipboard :refer [copy]])
  (:gen-class))
;;https://github.com/cldwalker/table

(def cli-options
  ;; An option with a required argument
  [["-l" "--length Length" "Password Length"
    :default 40
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-g" "--generate" "Generate new password"]
   [nil "--list"]])
(defn password-input []
  (println "master password(default : \"password\")")
  (String. (.readPassword (System/console))))
(defn -main [& args]
  (let [parsed-options (parse-opts args cli-options)
        url (first (:arguments parsed-options))
        username (second (:arguments parsed-options))
        options (:options parsed-options)]
    (cond
      (:generate options) (do
                            (stash/stash-init (password-input))
                            (let [password (generate-password (:length options))]
                              (db/insert-password url username)
                              (stash/add-password url username password)
                              (println "password added :" password)
                              (copy password)))
      (and url username) (do
                          (stash/stash-init (password-input))
                          (let [password (stash/find-password url username)]
                            (println "password :" password)
                            (copy password)
                            (println "password copied to clipboard")))
      (:list options) (t/table (db/list-passwords))
      :else (println "default"))))
(comment
  (-main)
  (t/table (db/list-passwords)))
(comment
  (generate-password 10))
