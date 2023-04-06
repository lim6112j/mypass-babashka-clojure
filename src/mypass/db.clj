(ns mypass.db
  (:require [babashka.pods :as pods]))
;;pods load babashka sqlite
(pods/load-pod 'org.babashka/go-sqlite3 "0.1.0")




