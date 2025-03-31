import { Button } from "@/components/ui/button";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import {
  ArrowBigRightDash,
  CreditCard,
  GoalIcon,
  Home,
  Menu,
  PiggyBankIcon,
} from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import { Profile } from "./Profile";

const routes = [
  {
    href: "/dashboard",
    label: "Dashboard",
    icon: Home,
  },
  {
    href: "/goals",
    label: "Metas",
    icon: GoalIcon,
  },
  {
    href: "/transactions",
    label: "Transações",
    icon: ArrowBigRightDash,
  },
  {
    href: "/accounts",
    label: "Contas",
    icon: CreditCard,
  },
  {
    href: "/budgets",
    label: "Orçamentos",
    icon: PiggyBankIcon,
  },
];

export function Header() {
  const location = useLocation();

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="flex h-16 justify-between px-4">
        <div className="flex flex-1 gap-6">
          <Sheet>
            <SheetTrigger asChild className="lg:hidden mt-3.5">
              <Button variant="ghost" size="icon" className="-ml-2">
                <Menu className="h-5 w-5" />
                <span className="sr-only">Abrir menu</span>
              </Button>
            </SheetTrigger>
            <SheetContent side="left" className="w-64">
              <SheetHeader className="border-b pb-4 mb-4">
                <SheetTitle>Menu</SheetTitle>
              </SheetHeader>
              <nav className="flex flex-col space-y-3">
                {routes.map((route) => {
                  const Icon = route.icon;
                  const isActive = location.pathname === route.href;
                  return (
                    <Link
                      key={route.href}
                      to={route.href}
                      className={`${
                        isActive
                          ? "bg-accent text-highlight font-bold"
                          : "hover:bg-accent"
                      }`}
                      style={{
                        borderRadius: "0.5rem",
                        padding: "0.5rem 1rem",
                        display: "flex",
                        alignItems: "center",
                        gap: "0.5rem",
                        fontSize: "1rem",
                        fontWeight: "500",
                        transition: "color 0.2s ease-in-out",
                      }}
                    >
                      <Icon className="h-4 w-4" />
                      <span
                        className={`${
                          isActive ? "text-highlight" : "text-foreground/80"
                        }`}
                      >
                        {route.label}
                      </span>
                    </Link>
                  );
                })}
              </nav>
            </SheetContent>
          </Sheet>

          <Link to="/dashboard" className="hidden lg:flex items-center gap-2">
            <span className="sr-only">Logo</span>
            <img src="/logo.svg" alt="Logo" className="h-12 w-12" />
          </Link>

          <nav className="hidden lg:flex flex-1 items-center gap-6">
            {routes.map((route) => (
              <Link
                key={route.href}
                to={route.href}
                style={{
                  color:
                    location.pathname === route.href
                      ? "var(--foreground)"
                      : "var(--foreground)/0.6",
                  transition: "color 0.2s ease-in-out",
                }}
                className={`flex items-center gap-2 text-sm font-medium ${
                  location.pathname === route.href
                    ? "text-highlight font-bold"
                    : "hover:text-foreground/80"
                }`}
              >
                <span
                  className={`${
                    location.pathname === route.href
                      ? "text-highlight"
                      : "text-foreground/80"
                  }`}
                >
                  {route.label}
                </span>
              </Link>
            ))}
          </nav>
        </div>

        <div className="flex items-center gap-2">
          <Profile />
        </div>
      </div>
    </header>
  );
}
