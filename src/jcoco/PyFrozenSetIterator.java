/**
 * <fileName>
 * Author: Kent D. Lee (c) 2017 Created on Mar 3, 2017.
 *
 * License: Please read the LICENSE file in this distribution for details
 * regarding the licensing of this code. This code is freely available for
 * educational use. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND.
 *
 * Description:
 */
package jcoco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

// Done
// Static?
public class PyFrozenSetIterator extends PyPrimitiveTypeAdapter {

    private PyFrozenSet frozenSet;
    private Iterator<PyObject> hashSetIterator;

    // Done
    // TODO
    public PyFrozenSetIterator(PyFrozenSet frozenSet) {
        super("frozen_set_iterator", PyType.PyTypeId.PyFrozenSetIteratorType);
        this.frozenSet = frozenSet;
        initMethods(funs());
        this.hashSetIterator=this.frozenSet.hashSet().iterator();
    }

    public static HashMap<String, PyCallable> funs() {
        HashMap<String, PyCallable> funs = new HashMap<String, PyCallable>();

        funs.put("__iter__", new PyCallableAdapter() {
            @Override
            public PyObject __call__(ArrayList<PyObject> args) {
                if (args.size() != 1) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION, "TypeError: expected 1 arguments, got " + args.size() + ".");
                }

                PyFrozenSetIterator self = (PyFrozenSetIterator) args.get(args.size() - 1);

                return self;
            }
        });

        funs.put("__next__", new PyCallableAdapter() {
            @Override
            public PyObject __call__(ArrayList<PyObject> args) {
                if (args.size() != 1) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION, "TypeError: expected 1 arguments, got " + args.size() + ".");
                }

                PyFrozenSetIterator self = (PyFrozenSetIterator) args.get(args.size() - 1);
                // TODO wrong method here?
                // What if you request iterators twice? Then the two iterators will
                // share the same static self, correct?
                // This is the problem for PyListIterator too
                if (self.hashSetIterator.hasNext()){
                    return self.hashSetIterator.next();
                }else{
                    throw new PyException(PyException.ExceptionType.PYSTOPITERATIONEXCEPTION, "stop it");
                }
            }
        });

        return funs;
    }
}
