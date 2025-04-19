import { Button } from "@/components/ui/button";
import { resendEmailConfirmation } from "@/services/userService";
import { Loader2 } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";

interface EmailConfirmationProps {
  userId: string;
}

export function EmailConfirmation({ userId }: EmailConfirmationProps) {
  const [isLoading, setIsLoading] = useState(false);

  const handleResend = () => {
    setIsLoading(true);
    resendEmailConfirmation(userId)
      .then(() => {
        toast.success("E-mail enviado com sucesso!");
      })
      .catch((error) => {
        console.error(error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

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
          <Button onClick={handleResend} variant="default" disabled={isLoading}>
            {isLoading ? (
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
