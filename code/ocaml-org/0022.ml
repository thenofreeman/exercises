(* Create a List Containing All Integers Within a Given Range *)
(* Level: Beginner *)

(* If first argument is greater than second, produce a list in decreasing order. *)

(* Example: *)
(* # range 4 9;; *)
(* - : int list = [4; 5; 6; 7; 8; 9] *)

let range a b l =
  let (start, rev) = if a < b then (a, false) else (b, true)
  let n = abs (a - b)

  let rec range s n acc = function
    | [] -> acc
    | hd :: tl ->
       if

  in let list = range start n acc l
  in if rev then List.rev list else list
