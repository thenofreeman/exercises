(* Palindrome *)
(* Level: Beginner *)

(* Find out whether a list is a palindrome. *)
(* Hint: A palindrome is its own reverse. *)

(* # is_palindrome ["x"; "a"; "m"; "a"; "x"];; *)
(* - : bool = true *)
(* # not (is_palindrome ["a"; "b"]);; *)
(* - : bool = true *)

let is_palindrome l =
  l = List.rev l;;

(* let is_palindrome l = *)
(*   let rec helper (l,rev_l) = *)
(*     match (l, rev_l) with *)
(*     | ([], []) -> true *)
(*     | ([], _) | (_, []) -> false *)
(*     | (hdl :: tll, hdr :: tlr) -> begin *)
(*         if hdl = hdr then helper (tll, tlr) *)
(*         else false *)
(*       end *)
(*   in helper (l, rev l);; *)
