(* N'th Element of a List *)
(* Level: Beginner *)

(* Find the N'th element of a list. *)

(* Example: *)
(* # at 2 ["a"; "b"; "c"; "d"; "e"];; *)
(* - : string option = Some "c" *)
(* # at 2 ["a"];; *)
(* - : string option = None *)

(* Remark: OCaml has List.nth which numbers elements from 0 and raises an exception if the index is out of bounds. *)
(* # List.nth ["a"; "b"; "c"; "d"; "e"] 2;; *)
(* - : string = "c" *)
(* # List.nth ["a"] 2;; *)
(* Exception: Failure "nth". *)

let rec at n = function
  | [] -> None
  | hd :: tl -> begin
      if n = 0 then Some hd
      else at (n-1) tl
    end;;
