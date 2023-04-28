(ns mypass.db
  (:require [babashka.pods :as pods]
            [honey.sql :as sql]
            [honey.sql.helpers :as h]
            [babashka.fs :as fs]))
;;pods load babashka sqlite
(pods/load-pod 'org.babashka/go-sqlite3 "0.1.0")
(require '[pod.babashka.go-sqlite3 :as sqlite])
(def dbname "mypass.db")

(defn create-db! []
  (when (not (fs/exists? dbname))
    (sqlite/execute! dbname
                     (-> (h/create-table :passwords)
                         (h/with-columns [[:url :text]
                                          [:username :text]
                                          [[:unique nil :url :username]]])
                         (sql/format)))))
(create-db!)
(defn insert-password [url username]
  (sqlite/execute! dbname
                   (-> (h/insert-into :passwords)
                       (h/columns :url :username)
                       (h/values
                        [[url username]])
                       (sql/format {:pretty true}))))

(defn list-passwords []
  (sqlite/query dbname
                (-> (h/select :url :username)
                    (h/from :passwords)
                    (sql/format))))

(comment
  (list-passwords)
  (->
   (h/select :url :username)
   (h/from :passwords)
   (sql/format))
  (insert-password "facebook.com" "benlim@gmail.com")
  (-> (h/insert-into :passwords)
      (h/columns :url :username)
      (h/values
       [["jon" "smith" 34]
        ["andrew" "Cooper" 12]
        ["Jane" "Daniels" 56]])
      (sql/format {:pretty true}))
  (create-db!)
  (fs/exists? dbname)
  (sqlite/execute! dbname
                   (-> (h/create-table :passwords)
                       (h/with-columns [[:url :text]
                                        [:username :text]
                                        [[:unique nil :url :username]]])
                       (sql/format))))
