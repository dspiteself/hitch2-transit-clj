(ns hitch2.transit.basic-test
  (:require [clojure.test :refer [deftest is testing]]
            [cognitect.transit :as t]
            [hitch2.transit.basic :refer [reader writer]]
            [hitch2.descriptor :refer [->Descriptor]])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))

(deftest basic
  (testing "roundtrip"
    (let [sample (->Descriptor (symbol "blah.cal") {:hi "there"})
          out (ByteArrayOutputStream. 4096)
          w (writer out :json)
          _ (t/write w sample)
          encoded (.toString out)
          in (ByteArrayInputStream. (.toByteArray out))
          r (reader in :json)

          ]
      ;["~#DTOR",["~$blah.cal",["^ ","~:hi","there"]]]
      (is (= encoded "[\"~#DTOR\",[\"~$blah.cal\",[\"^ \",\"~:hi\",\"there\"]]]"))
      (is (= (t/read r) sample)))))
