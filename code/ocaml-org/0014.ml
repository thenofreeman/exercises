(* Duplicate the Elements of a List *)
(* Level: Beginner *)

(* Duplicate the elements of a list. *)

(* Example: *)
(* # duplicate ["a"; "b"; "c"; "c"; "d"];; *)
(* - : string list = ["a"; "a"; "b"; "b"; "c"; "c"; "c"; "c"; "d"; "d"] *)

let duplicate l =
  let rec duplicate acc = function
    | [] -> acc
    | hd :: tl -> duplicate (hd :: hd :: acc) tl
  in List.rev (duplicate [] l);;
