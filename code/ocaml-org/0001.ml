(* Tail of a List *)
(* Level: Beginner *)

(* Write a function last : 'a list -> 'a option that returns the last element of a list. *)

(* Example: *)
(* # last ["a" ; "b" ; "c" ; "d"];; *)
(* - : string option = Some "d" *)
(* # last [];; *)
(* - : 'a option = None *)

let rec last = function
  | [] -> None
  | [x] -> Some x
  | _ :: tl -> last tl;;
