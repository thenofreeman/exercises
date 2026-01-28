(* Reverse a List *)
(* Level: Beginner *)

(* Reverse a list. *)
(* OCaml standard library has List.rev but we ask that you reimplement it. *)

(* Example: *)
(* # rev ["a"; "b"; "c"];; *)
(* - : string list = ["c"; "b"; "a"] *)

let rev l =
  let rec rev acc = function
    | [] -> acc
    | hd :: tl -> rev (hd :: acc) tl
  in rev [] l;;
