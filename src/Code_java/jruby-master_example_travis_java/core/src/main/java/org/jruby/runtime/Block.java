/*
 ***** BEGIN LICENSE BLOCK *****
 * Version: EPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Eclipse Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/epl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2002-2004 Anders Bengtsson <ndrsbngtssn@yahoo.se>
 * Copyright (C) 2001-2004 Jan Arne Petersen <jpetersen@uni-bonn.de>
 * Copyright (C) 2002 Benoit Cerrina <b.cerrina@wanadoo.fr>
 * Copyright (C) 2004-2007 Thomas E Enebo <enebo@acm.org>
 * Copyright (C) 2004 Stefan Matthias Aust <sma@3plus4.de>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the EPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the EPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/


////////////////////////////////////////////////////////////////////////////////
// NOTE: THIS FILE IS GENERATED! DO NOT EDIT THIS FILE!
// generated from: src/org/jruby/runtime/Block.erb
// using arities: src/org/jruby/runtime/Block.arities.erb
////////////////////////////////////////////////////////////////////////////////


package org.jruby.runtime;

import java.util.Objects;
import org.jruby.EvalType;
import org.jruby.RubyProc;
import org.jruby.ir.runtime.IRRuntimeHelpers;
import org.jruby.runtime.builtin.IRubyObject;

/**
 *  Internal live representation of a block ({...} or do ... end).
 */
public class Block {
    public enum Type {
        NORMAL(false), PROC(false), LAMBDA(true), THREAD(false);

        Type(boolean checkArity) {
            this.checkArity = checkArity;
        }

        public final boolean checkArity;
    }

    /**
     * The Proc that this block is associated with.  When we reference blocks via variable
     * reference they are converted to Proc objects.  We store a reference of the associated
     * Proc object for easy conversion.
     */
    private RubyProc proc = null;

    public Type type = Type.NORMAL;

    private final Binding binding;

    private final BlockBody body;

    /** Whether this block and any clones of it should be considered "escaped" */
    private boolean escaped;

    /** What block to use for determining escape; defaults to this */
    private Block escapeBlock = this;

    /**
     * All Block variables should either refer to a real block or this NULL_BLOCK.
     */
    public static final Block NULL_BLOCK = new Block(BlockBody.NULL_BODY);

    public Block(BlockBody body, Binding binding) {
        assert binding != null;
        this.body = body;
        this.binding = binding;
    }

    public Block(BlockBody body) {
        this(body, Binding.DUMMY);
    }

    public DynamicScope allocScope(DynamicScope parentScope) {
        // SSS: Important!  Use getStaticScope() to use a copy of the static-scope stored in the block-body.
        // Do not use 'closure.getStaticScope()' -- that returns the original copy of the static scope.
        // This matters because blocks created for Thread bodies modify the static-scope field of the block-body
        // that records additional state about the block body.
        //
        // FIXME: Rather than modify static-scope, it seems we ought to set a field in block-body which is then
        // used to tell dynamic-scope that it is a dynamic scope for a thread body.  Anyway, to be revisited later!
        EvalType evalType = ((IRBlockBody)body).getEvalType();
        DynamicScope newScope = DynamicScope.newDynamicScope(body.getStaticScope(), parentScope, evalType);
        if (type == Block.Type.LAMBDA) newScope.setLambda(true);
        return newScope;
    }

    public EvalType getEvalType() {
        // SSS FIXME: This is smelly
        return body instanceof IRBlockBody ? ((IRBlockBody)body).getEvalType() : null;
    }

    public void setEvalType(EvalType evalType) {
        body.setEvalType(evalType);
    }

    public IRubyObject call(ThreadContext context, IRubyObject[] args) {
        return body.call(context, this, args);
    }

    public IRubyObject call(ThreadContext context, IRubyObject[] args, Block blockArg) {
        return body.call(context, this, args, blockArg);
    }

    public IRubyObject call(ThreadContext context) {
        return body.call(context, this);
    }
    public IRubyObject call(ThreadContext context, Block blockArg) {
        return body.call(context, this, blockArg);
    }
    public IRubyObject yieldSpecific(ThreadContext context) {
        return body.yieldSpecific(context, this);
    }
    public IRubyObject call(ThreadContext context, IRubyObject arg0) {
        return body.call(context, this, arg0);
    }
    public IRubyObject call(ThreadContext context, IRubyObject arg0, Block blockArg) {
        return body.call(context, this, arg0, blockArg);
    }
    public IRubyObject yieldSpecific(ThreadContext context, IRubyObject arg0) {
        return body.yieldSpecific(context, this, arg0);
    }
    public IRubyObject call(ThreadContext context, IRubyObject arg0, IRubyObject arg1) {
        return body.call(context, this, arg0, arg1);
    }
    public IRubyObject call(ThreadContext context, IRubyObject arg0, IRubyObject arg1, Block blockArg) {
        return body.call(context, this, arg0, arg1, blockArg);
    }
    public IRubyObject yieldSpecific(ThreadContext context, IRubyObject arg0, IRubyObject arg1) {
        return body.yieldSpecific(context, this, arg0, arg1);
    }
    public IRubyObject call(ThreadContext context, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2) {
        return body.call(context, this, arg0, arg1, arg2);
    }
    public IRubyObject call(ThreadContext context, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, Block blockArg) {
        return body.call(context, this, arg0, arg1, arg2, blockArg);
    }
    public IRubyObject yieldSpecific(ThreadContext context, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2) {
        return body.yieldSpecific(context, this, arg0, arg1, arg2);
    }

    public IRubyObject yield(ThreadContext context, IRubyObject value) {
        return body.yield(context, this, value);
    }

    public IRubyObject yieldNonArray(ThreadContext context, IRubyObject value, IRubyObject self) {
        return body.yield(context, this, new IRubyObject[] { value }, self);
    }

    public IRubyObject yieldArray(ThreadContext context, IRubyObject value, IRubyObject self) {
        // SSS FIXME: Later on, we can move this code into IR insructions or
        // introduce a specialized entry-point when we know that this block has
        // explicit call protocol IR instructions.
        IRubyObject[] args = IRRuntimeHelpers.singleBlockArgToArray(value);
        return body.yield(context, this, args, self);
    }

    public IRubyObject yieldValues(ThreadContext context, IRubyObject[] args) {
        return body.yield(context, this, args, null);
    }

    public Block cloneBlock() {
        Block newBlock = new Block(body, binding);

        newBlock.type = type;
        newBlock.escapeBlock = this;

        return newBlock;
    }

    public Block cloneBlockAndFrame() {
        Binding oldBinding = binding;
        Binding binding = new Binding(
                oldBinding.getSelf(),
                oldBinding.getFrame().duplicate(),
                oldBinding.getVisibility(),
                oldBinding.getDynamicScope(),
                oldBinding.getMethod(),
                oldBinding.getFile(),
                oldBinding.getLine());

        Block newBlock = new Block(body, binding);

        newBlock.type = type;
        newBlock.escapeBlock = this;

        return newBlock;
    }

    public Block cloneBlockForEval(IRubyObject self, EvalType evalType) {
        Block block = cloneBlock();

        block.getBinding().setSelf(self);
        block.getBinding().getFrame().setSelf(self);
        block.setEvalType(evalType);

        return block;
    }

    /**
     * What is the arity of this block?
     *
     * @return the arity
     */
    @Deprecated
    public Arity arity() {
        return getSignature().arity();
    }

    public Signature getSignature() {
        return body.getSignature();
    }

    /**
     * Retrieve the proc object associated with this block
     *
     * @return the proc or null if this has no proc associated with it
     */
    public RubyProc getProcObject() {
    	return proc;
    }

    /**
     * Set the proc object associated with this block
     *
     * @param procObject
     */
    public void setProcObject(RubyProc procObject) {
    	this.proc = procObject;
    }

    /**
     * Is the current block a real yield'able block instead a null one
     *
     * @return true if this is a valid block or false otherwise
     */
    public final boolean isGiven() {
        return this != NULL_BLOCK;
    }

    public Binding getBinding() {
        return binding;
    }

    public BlockBody getBody() {
        return body;
    }

    /**
     * Gets the frame.
     *
     * @return Returns a RubyFrame
     */
    public Frame getFrame() {
        return binding.getFrame();
    }

    public boolean isEscaped() {
        return escapeBlock.escaped;
    }

    public void escape() {
        escapeBlock.escaped = true;
    }

    @Override
    public boolean equals(Object other) {
        if ( this == other ) return true;
        if ( ! ( other instanceof Block ) ) return false;

        final Block that = (Block) other;

        return this.binding.equals(that.binding) && this.body == that.body;
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 13 * hash + Objects.hashCode(this.binding);
        hash = 17 * hash + Objects.hashCode(this.body);
        return hash;
    }

}