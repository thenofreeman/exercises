(* Insert an Element at a Given Position Into a List *)
(* Level: Beginner *)

(* Start counting list elements with 0. If the position is larger or equal to the length of the list, insert the element at the end. *)
(* (The behavior is unspecified if the position is negative.) *)

(* Example: *)
(* # insert_at "alfa" 1 ["a"; "b"; "c"; "d"];; *)
(* - : string list = ["a"; "alfa"; "b"; "c"; "d"] *)

let rec insert_at v idx = function
  | [] -> [v]
  | hd :: tl ->
     if idx <= 0 then v :: hd :: tl
     else hd :: (insert_at v (idx - 1) tl);;
