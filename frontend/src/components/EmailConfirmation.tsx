import { Button } from "@/components/ui/button";
import { resendEmailConfirmation } from "@/services/userService";
import { useMutation } from "@tanstack/react-query";
import { Loader2 } from "lucide-react";
import { toast } from "sonner";

interface EmailConfirmationProps {
  userId: string;
}

export function EmailConfirmation({ userId }: EmailConfirmationProps) {
  const mutation = useMutation({
    mutationFn: resendEmailConfirmation,
    onSuccess: () => {
      toast.success("E-mail reenviado com sucesso!");
    },
    onError: () => {
      toast.error(mutation.data);
    },
  });

  return (
    <div className="flex items-center justify-center h-full">
      <div className="max-w-md mx-auto bg-background rounded-lg shadow-lg p-4">
        <h1 className="text-center text-3xl font-bold mb-4">
          Confirme seu e-mail
        </h1>
        <p className="text-center text-lg mb-4">
          Enviamos um e-mail para você com instruções para confirmar sua conta.
        </p>
        <p className="text-center text-lg mb-4">
          Se você não recebeu o e-mail, verifique sua caixa de spam.
        </p>
        <p className="text-center text-lg mb-8">
          Se você ainda não recebeu o e-mail, clique no botão abaixo para
          reenviar.
        </p>
        <div className="flex items-center justify-center">
          <Button
            onClick={() => mutation.mutate(userId)}
            variant="default"
            disabled={mutation.isPending}
          >
            {mutation.isPending ? (
              <Loader2 className="h-4 w-4 animate-spin" />
            ) : (
              "Reenviar"
            )}
          </Button>
        </div>
      </div>
    </div>
  );
}
