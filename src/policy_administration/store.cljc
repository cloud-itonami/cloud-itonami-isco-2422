(ns policy-administration.store
  "SSoT for the ISCO-08 2422 independent policy-administration sole-
  proprietor actor. Store is a protocol injected into the
  `policy-administration.actor` StateGraph — `MemStore` is the
  default, deterministic, zero-dep backend; a Datomic/kotoba-server-
  backed implementation can be swapped in without touching the actor or
  governor (itonami actor pattern, per ADR-2607011000 / CLAUDE.md
  Actors section).

  Domain:

    client   — a registered policy-administration client (:client-id,
               :name)
    record   — a committed operating record under a client (draft
               note, published policy, legal-standing-affecting
               publication, confidential-draft disclosure) — written
               ONLY via commit-record!, never mutated in place
    ledger   — an append-only audit trail of every proposal/verdict/
               disposition, regardless of outcome (commit or hold)")

(defprotocol Store
  (client [s client-id])
  (records-of [s client-id])
  (ledger [s])
  (register-client! [s client])
  (commit-record! [s record])
  (append-ledger! [s fact]))

(defrecord MemStore [a]
  Store
  (client [_ client-id] (get-in @a [:clients client-id]))
  (records-of [_ client-id] (filter #(= client-id (:client-id %)) (:records @a)))
  (ledger [_] (:ledger @a))
  (register-client! [s client]
    (swap! a assoc-in [:clients (:client-id client)] client) s)
  (commit-record! [s record]
    (swap! a update :records (fnil conj []) record) s)
  (append-ledger! [s fact]
    (swap! a update :ledger (fnil conj []) fact) s))

(defn mem-store
  ([] (mem-store {}))
  ([seed] (->MemStore (atom (merge {:clients {} :records [] :ledger []} seed)))))
