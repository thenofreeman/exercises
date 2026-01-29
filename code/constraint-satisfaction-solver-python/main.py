import sys
import operator as op

def main():
    nargs = len(sys.argv) - 1
    if nargs != 3:
        sys.stdout.write("Improper number of arguments given.\n")
        sys.stdout.write(f"Expected 3, given {nargs}.\n")
        sys.exit(1)

    vs = parse_vars_file(sys.argv[1])
    cs = parse_cons_file(sys.argv[2])

    ce_procedure = sys.argv[3].lower() 
    match ce_procedure:
        case 'none':
            csp_backtracking(vs, cs)
        case 'fc' :
            csp_fwdchecking(vs, cs)
        case _ :
            sys.stdout.write("Incorrect param for consistency-enforcing procedure.\n")
            sys.stdout.write(f"Expected 'none' or 'fc', given {ce_procedure.lower()}.\n")
            sys.exit(1)

def parse_vars_file(filename: str):
    vs = dict()
    with open(filename, 'r')as f:
        for line in f:
            [letter, values] = line.split(':')
            vs[letter.strip()] = [int(value.strip()) for value in values.strip().split(' ')]

    return vs

def parse_cons_file(filename: str):
    cs = dict()
    with open(filename, 'r')as f:
        for line in f:
            [left, oper, right] = line.strip().split(' ')

            match oper:
                case '>':
                    append_to_keylist(cs, left, (op.gt, right))
                    append_to_keylist(cs, right, (op.lt, left))
                case '<':
                    append_to_keylist(cs, left, (op.lt, right))
                    append_to_keylist(cs, right, (op.gt, left))
                case '!':
                    append_to_keylist(cs, left, (op.ne, right))
                    append_to_keylist(cs, right, (op.ne, left))
                case '=':
                    append_to_keylist(cs, left, (op.eq, right))
                    append_to_keylist(cs, right, (op.eq, left))
                    pass
                case _:
                    pass

    return cs

def append_to_keylist(keylist, key, value):
    if key not in keylist:
        keylist[key] = []

    keylist[key].append(value)

def csp_backtracking(vs, cs):
    csp(vs, cs, dict(), False)

def csp_fwdchecking(vs, cs):
    csp(vs, cs, dict(), True)

def csp(vs_orig, cs, assigned, forward_checking):
    def ordered_lcv(vs, mcv):
        vs = vs.copy()

        lst = []
        domain = vs[mcv][:]

        while domain:
            vs[mcv] = domain
            lcv = least_constraining_val(mcv, vs, cs)

            if lcv is None:
                if domain:
                    lcv = domain[0]
                else:
                    break

            lst.append(lcv)

            if lcv in domain:
                domain.remove(lcv)
        
        return lst
    
    def do_fwdchecking(vs, mcv, lcv, assignments):
        clear = False

        domains = { var: list(domain) for var, domain in vs.items() }

        if mcv in cs:
            for fn, rhs in cs[mcv]:
                if rhs in vs:
                    new_domain = [val for val in vs[rhs] if fn(lcv, val)]
                    
                    if not new_domain:
                        clear = True
                        break

                    vs[rhs] = new_domain
        
        if clear:
            assignment_str = ", ".join([f"{k}={v}" for k, v in assignments.items()])
            sys.stdout.write(f"{assignment_str}  failure\n")

            for var, domain in domains.items():
                if var in vs:
                    vs[var] = domain

            return True

        return False


    def do_backtracking(vs, assignments):
        if not vs:
            assignment_str = ", ".join([f"{k}={v}" for k, v in assignments.items()])
            sys.stdout.write(f"{assignment_str}  solution\n")

            return assignments

        mcv = most_constrained_var(vs, cs)
        
        for lcv in ordered_lcv(vs, mcv):
            new_assignments = assignments.copy()
            new_assignments[mcv] = lcv

            if valid_assignment(mcv, lcv, assignments, cs):
                new_vs = vs.copy()
                del new_vs[mcv]

                if forward_checking and do_fwdchecking(new_vs, mcv, lcv, new_assignments):
                    continue

                result = do_backtracking(new_vs, new_assignments)

                if result is not None:
                    return result
            else:            
                assignment_str = ", ".join([f"{k}={v}" for k, v in new_assignments.items()])
                sys.stdout.write(f"{assignment_str}  failure\n")
            
        return None

    return do_backtracking(vs_orig.copy(), assigned)

def valid_assignment(mcv, lcv, assignments, cs):
    if mcv in cs:
        for fn, rhs in cs[mcv]:
            if rhs in assignments:
                rhs = assignments[rhs]

                if not fn(lcv, rhs):
                    return False

    return True

def most_constrained_var(vs, cs):
    minv = float('inf')
    mcv = None

    for k, v in vs.items():
        if len(v) < minv:
            mcv = k
            minv = len(v)
        elif len(v) == minv:
            if mcv in cs and k in cs:
                mcv = more_constraining_var({
                    k: cs[k], 
                    mcv: cs[mcv]
                })

    return mcv

def more_constraining_var(cs):
    maxv = -1
    mcv = None

    for k, v in cs.items():
        if len(v) > maxv:
            mcv = k
            maxv = len(v)
        elif len(v) == maxv:
            if k < mcv:
                mcv = k

    return mcv

def least_constraining_val(var, vs, cs):
    maxvars = 0
    lcv = None

    for val in vs[var]:
        nvars = 0
        if var in cs:
            for con in cs[var]:
                if con[1] in vs:
                    lst = list(filter(lambda rhs: con[0](val, rhs), vs[con[1]]))
                    nvars += len(lst)

            if nvars > maxvars:
                maxvars = nvars 
                lcv = val

        if nvars > maxvars:
            maxvars = nvars
            lcv = val

    if lcv is None and vs[var]:
        return vs[var][0]

    return lcv

if __name__ == '__main__':
    main()