(* Length of a List (bonus) *)
(* Level: Beginner *)

(* Find the number of elements of a list. Use tail recursion. *)
(* OCaml standard library has List.length but we ask that you reimplement it.*)

(* Example: *)
(* # length ["a"; "b"; "c"];; *)
(* - : int = 3 *)
(* # length [];; *)
(* - : int = 0 *)

let length_tr l =
  let rec length n = function
    | [] -> n
    | _ :: tl -> length (n+1) tl
  in length 0 l;;
