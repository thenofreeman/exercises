(* Last Two Elements of a List *)
(* Level: Beginner *)

(* Find the last two (last and penultimate) elements of a list. *)

(* Example: *)
(* # last_two ["a"; "b"; "c"; "d"];; *)
(* - : (string * string) option = Some ("c", "d") *)
(* # last_two ["a"];; *)
(* - : (string * string) option = None *)

let rec last_two = function
  | []
  | [_] -> None
  | x :: y :: [] -> Some (x, y)
  | _ :: tl -> last_two tl;;
