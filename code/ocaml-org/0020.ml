(* Remove the K'th Element From a List *)
(* Level: Beginner *)

(* Remove the K'th element from a list. *)
(* The first element of the list is numbered 0, the second 1,... *)

(* Example: *)
(* # remove_at 1 ["a"; "b"; "c"; "d"];; *)
(* - : string list = ["a"; "c"; "d"] *)

let rec remove_at idx = function
  | [] -> []
  | hd :: tl ->
     if idx = 0 then tl
     else hd :: (remove_at (idx - 1) tl);;
