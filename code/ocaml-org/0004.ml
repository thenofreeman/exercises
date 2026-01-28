(* Length of a List *)
(* Level: Beginner *)

(* Find the number of elements of a list. *)
(* OCaml standard library has List.length but we ask that you reimplement it. *)

(* Example: *)
(* # length ["a"; "b"; "c"];; *)
(* - : int = 3 *)
(* # length [];; *)
(* - : int = 0 *)

let rec length = function
  | [] -> 1
  | _ :: tl -> 1 + length tl;;
